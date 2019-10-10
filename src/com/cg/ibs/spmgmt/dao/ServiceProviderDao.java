package com.cg.ibs.spmgmt.dao;

import java.util.ArrayList;
import com.cg.ibs.spmgmt.bean.ServiceProvider;
import com.cg.ibs.spmgmt.exception.IBSException;
import com.cg.ibs.spmgmt.exception.RegisterException;

public interface ServiceProviderDao {

	boolean storeServiceProviderData(ServiceProvider serviceProvider) throws RegisterException;

	ArrayList<ServiceProvider> fetchPendingSp();

	boolean checkLogin(String username, String password) throws IBSException;

	void approveStatus(ServiceProvider serviceProvider) throws IBSException;

	boolean checkUserID(String userId);

	ArrayList<ServiceProvider> fetchApprovedSp() ;
	
    ArrayList<ServiceProvider> fetchHistory();

	boolean checkAdminLogin(String adminID, String adminPassword) throws IBSException;

	ServiceProvider getServiceProvider(String uid) throws IBSException;

	boolean emptyData();

}
