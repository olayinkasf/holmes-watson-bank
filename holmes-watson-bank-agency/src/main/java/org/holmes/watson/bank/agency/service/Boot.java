/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.holmes.watson.bank.agency.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.holmes.watson.bank.agency.entity.ClientJpaController;
import static org.holmes.watson.bank.agency.service.AccountServiceImpl.ACCOUNT_SERVICE;
import static org.holmes.watson.bank.agency.service.AuthServiceImpl.AUTH_SERVICE;
import static org.holmes.watson.bank.agency.service.TransactionServiceImpl.TRANSACTION_SERVICE;
import org.holmes.watson.bank.agency.view.AgencyView;
import org.holmes.watson.bank.core.AccountService;
import org.holmes.watson.bank.core.HolmesWatson;
import org.holmes.watson.bank.core.TransactionService;
import org.holmes.watson.bank.core.auth.AuthService;
import org.holmes.watson.bank.core.entity.Agency;

/**
 *
 * @author Olayinka
 */
public class Boot {

    public static final String AGENCY_KEY = "agency.key";
    public static final String ADDRESS_KEY = "agency.host";
    public static final String NAME_KEY = "agency.name";

    private static final String DB_USER_NAME = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static BackUpService backUpService;
    private static final String HQ_HOST = "hq.host";

    public static void main(String... args) throws RemoteException, FileNotFoundException, IOException, NotBoundException {
        System.setSecurityManager(new RMISecurityManager());

        File propertyFile = new File(HolmesWatson.PROPERTY_FILE_NAME);
        Properties properties;

        try (InputStream is = new FileInputStream(propertyFile)) {
            properties = new Properties();
            properties.load(is);
        }

        String agencyKey = properties.getProperty(AGENCY_KEY);
        String agencyAddress = properties.getProperty(ADDRESS_KEY);
        String hqIp = properties.getProperty(HQ_HOST, HolmesWatson.HEADQUATERS_ADDRESS);

        Agency agency = new Agency(agencyKey, agencyAddress);
        Agency.setAgency(agency);
        String dbUser = properties.getProperty(DB_USER_NAME);
        String dbPassword = properties.getProperty(DB_PASSWORD);

        if (agencyKey == null || agencyAddress == null || hqIp == null || dbUser == null || dbPassword == null) {
            System.err.println("Please set properties in root directory");
            return;
        }

        Registry registry = LocateRegistry.getRegistry(properties.getProperty("hq.host", hqIp), HolmesWatson.HQ_PORT);
        AuthService authService = (AuthService) registry.lookup(AuthService.SERVICE_NAME);
        TransactionService transactionService = (TransactionService) registry.lookup(TransactionService.SERVICE_NAME);
        AccountService accountService = (AccountService) registry.lookup(AccountService.SERVICE_NAME);
        backUpService = (BackUpService) registry.lookup(BackUpService.SERVICE_NAME);
        ((AuthServiceImpl) AUTH_SERVICE).setHqService(authService);
        ((TransactionServiceImpl) TRANSACTION_SERVICE).setHqService(transactionService);
        ((AccountServiceImpl) ACCOUNT_SERVICE).setHqService(accountService);

        EntityManagerFactory managerFactory;
        Map<String, String> persistenceMap = new HashMap<>();
        persistenceMap.put("javax.persistence.jdbc.user", dbUser);
        persistenceMap.put("javax.persistence.jdbc.password", dbPassword);
        managerFactory = Persistence.createEntityManagerFactory("HOLMESWATSON", persistenceMap);

        ClientJpaController cjc = new ClientJpaController(managerFactory);
        System.out.println(cjc.findClient(""));

        ((AuthServiceImpl) AUTH_SERVICE).setEmf(managerFactory);
        ((TransactionServiceImpl) TRANSACTION_SERVICE).setEmf(managerFactory);
        ((AccountServiceImpl) ACCOUNT_SERVICE).setEmf(managerFactory);

        registry = LocateRegistry.createRegistry(HolmesWatson.PORT);
        registry.rebind(AuthService.SERVICE_NAME, UnicastRemoteObject.exportObject(AUTH_SERVICE, HolmesWatson.PORT));
        registry.rebind(AccountService.SERVICE_NAME, UnicastRemoteObject.exportObject(ACCOUNT_SERVICE, HolmesWatson.PORT));
        registry.rebind(TransactionService.SERVICE_NAME, UnicastRemoteObject.exportObject(TRANSACTION_SERVICE, HolmesWatson.PORT));
        new AgencyView().setVisible(true);
        new Thread(new BackUpThread(backUpService, cjc)).start();
        BackUpService.scheduler.schedule(new BackUpThread(backUpService, cjc), 6, TimeUnit.HOURS);

    }

}
