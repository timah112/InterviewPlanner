package com.interviewplanner.web.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.interviewplanner.web.bean.UserBean;
import com.interviewplanner.web.entity.UserEntity;
import com.interviewplanner.web.enums.Gender;

public class UserInfoDao {
	protected SessionFactory sessionFactory;

	public String userInfoSave(UserBean userBean) {

		Object UserBeanObj = userObj(userBean);

		// Object tUserObj = tUser;
		Transaction tx = null;
		try {
			// create session factory
			Configuration con = new Configuration().configure().addAnnotatedClass(UserEntity.class);
			// create session
			SessionFactory sf = con.buildSessionFactory();

			// start a transaction
			Session session = sf.openSession();
			// save the entity object
			tx = session.beginTransaction();
			session.save(UserBeanObj); // this is similar to the Entity Manager class from JPA, that uses the persist
										// method to insert data to the database
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return "fail";
		}

		String successOutput = "successful entry!";
		System.out.println("HAHAHAH");
		return successOutput;
	}

	public UserEntity userObj(UserBean userBean) {

		UserEntity tUser = new UserEntity();
		if (userBean.getUserId() == null) {
			String userId = userBean.getFirstName().concat("111").trim();
			
			tUser.setUserId(userId);
		}

		tUser.setFirstName(userBean.getFirstName());
		tUser.setLastName(userBean.getLastName());
		// tUser.setGender(userBean.getGender());
		// SET Other Values for TUser Table from other POJOS!

		return tUser;

	}
}
