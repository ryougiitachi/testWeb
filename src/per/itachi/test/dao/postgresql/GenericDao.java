package per.itachi.test.dao.postgresql;

import java.io.Serializable;
import java.util.List;

/**
 * Hibernate编程模式
 * A single hibernate session might have the same scope as a single database transaction. <br/>
 * The most common programming model is used for <i>session-per-request</i> implementation pattern. <br/>
 * <br/>
 * And <i>session-per-operation</i> anti-pattern is inappropriate for application. <br/>
 * <br/>
 * Hibernate recommends that <i>session-per-conversation</i> pattern, 
 * in a case that a single Session has a bigger scope than a single database transaction. <br/> 
 * */
public interface GenericDao<T, PK extends Serializable> {
	
	T load(PK id);

	T get(PK id);

	List<T> findAll();

	void persist(T entity);

	PK save(T entity);
	
	void update(T entity);

	void saveOrUpdate(T entity);

	void delete(PK id);

	void flush();
}
