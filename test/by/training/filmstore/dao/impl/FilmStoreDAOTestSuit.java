package by.training.filmstore.dao.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite; 

@Suite.SuiteClasses({ActorDAOTest.class,
			  FilmDirectorDAOTest.class,
			  		 FilmDAOTest.class,
			  		 UserDAOTest.class,
			  		OrderDAOTest.class,
			  	  CommentDAOTest.class,
			  GoodOfOrderDAOTest.class})
@RunWith(Suite.class)
public final class FilmStoreDAOTestSuit {

}
