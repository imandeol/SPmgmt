package com.cg.ibs.spmgmt.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cg.ibs.spmgmt.bean.BankAdmin;
import com.cg.ibs.spmgmt.bean.ServiceProvider;
import com.cg.ibs.spmgmt.exception.IBSException;
import com.cg.ibs.spmgmt.exception.IBSExceptionInterface;
import com.cg.ibs.spmgmt.exception.RegisterException;

public class ServiceProviderDaoImpl implements ServiceProviderDao {
	
	//Bank Administrative Database
	BankAdmin admin1 = new BankAdmin("id1", "pass1");
	BankAdmin admin2 = new BankAdmin("id2", "pass2");

	// DataBase Initialization
	private static Map<String, ServiceProvider> spMap = new HashMap<>();
	private static Map<String, BankAdmin> bankMap = new HashMap<>();
	private static ArrayList<ServiceProvider> pendingList = new ArrayList<>();
	private static ArrayList<ServiceProvider> approvedList = new ArrayList<ServiceProvider>();
	{
		bankMap.put(admin1.getAdminID(), admin1);
		bankMap.put(admin2.getAdminID(), admin2);
	}

	// Storing Service Provider Details in HashMap
	@Override
	public boolean storeServiceProviderData(ServiceProvider serviceProvider) throws RegisterException {
		if (serviceProvider != null && !(spMap.containsKey(serviceProvider.getUserId()))) {
			spMap.put(serviceProvider.getUserId(), serviceProvider);
			return true;
		} else {
			throw new RegisterException(IBSExceptionInterface.ALREADY_EXISTS_MESSAGE);//
		}
	}
	
	//All the pending Service Providers being stored in TreeMap sorted on the basis of Date and Time of registration
	@Override
	public ArrayList<ServiceProvider> fetchPendingSp() {
		for (ServiceProvider serviceProvider : spMap.values()) {
			if (serviceProvider.getStatus().equalsIgnoreCase("pending")) {
				pendingList.add(serviceProvider);
			}
		}
		return pendingList;
	}

	//Function to check the Login credentials of User
	@Override
	public boolean checkLogin(String userId, String password) throws IBSException {
		boolean result=false;
		for (ServiceProvider serviceProvider : spMap.values()) {
			if (serviceProvider.getUserId().equals(userId)) {
				if (serviceProvider.getPassword().equals(password)) {
					result=true;
				}
				else{
				throw new IBSException(IBSExceptionInterface.INCORRECT_PASSWORD_MESSAGE);//
				}
			}
		}
		return result;
	}
	
	//Changing the status of User based on the Bank Administrative's decision
	@Override
	public void approveStatus(ServiceProvider serviceProvider) throws IBSException {
		spMap.replace(serviceProvider.getUserId(), serviceProvider);
	}
	
	//Function to see if the userID is present in Map or not
	@Override
	public boolean checkUserID(String userId){
		boolean result;
		if (spMap.containsKey(userId)) {
			result = false;
		} else
			result = true;
		return result;
	}
	
	//All the Approved Service Provider Details for Remittance Management
	@Override
	public ArrayList<ServiceProvider> fetchApprovedSp() {
		for (ServiceProvider serviceProvider : spMap.values()) {
			if (serviceProvider.getStatus().equalsIgnoreCase("Approved")) {
				approvedList.add(serviceProvider);
			}
		}
		return approvedList;
	}
	
	//Administrative Login credentials check
	@Override
	public boolean checkAdminLogin(String adminID, String adminPassword) throws IBSException {
		boolean result = false;
		for (BankAdmin bankAdmin : bankMap.values()) {
			if (bankAdmin.getAdminID().equals(adminID) && bankAdmin.getAdminPassword().equals(adminPassword)) {
				result = true;
			}
		}
		return result;
	}
	
	//Function to get details of Service Provider stored in HashMap
	@Override
	public ServiceProvider getServiceProvider(String uid) throws IBSException {
		if (spMap.containsKey(uid)) {
			return spMap.get(uid);
		}
		return null;
	}

	//Default empty Data Function
	@Override
	public boolean emptyData() {
		return spMap.isEmpty();
	}
}
