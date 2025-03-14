package com.example.sample1app;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

/**
 * PersonDAO の実装クラス（リポジトリ層）
 * Person エンティティのデータを取得するための DAO クラス
 */
@Repository  // Spring のリポジトリ（DAO）クラスとして認識させる
public class PersonDAOPersonImpl implements PersonDAO<Person> {
    
    private static final long serialVersionUID = 1L;  // シリアル化のための ID（使われていないが一応定義）

    @PersistenceContext  // JPA のエンティティマネージャを Spring に依存注入
    private EntityManager entityManager;
    
    /**
     * デフォルトコンストラクタ
     */
    public PersonDAOPersonImpl() {
        super();
    }

    /**
     * Person エンティティの全レコードを取得する
     * @return Person のリスト
     */
    @Override
    public List<Person> getAll() {
//        // HQL（Hibernate Query Language）を使って、"Person" テーブルの全レコードを取得するクエリを作成
//        Query query = entityManager.createQuery("from Person");
//
//        // クエリの実行結果をリストにキャスト
//        @SuppressWarnings("unchecked")  // クエリ結果の型チェックを抑制（List<Person> にキャスト）
//        List<Person> list = query.getResultList();
//
//        // EntityManager を閉じる（通常は閉じなくてもよいが、ここでは明示的に閉じている）
//        entityManager.close();
//
//        return list;
    	
//    	List<Person> list = null;
//    	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//    	CriteriaQuery<Person> query = builder.createQuery(Person.class);
//    	
//    	Root<Person> root = query.from(Person.class);
//    	query.select(root);
//    	
//    	list = (List<Person>)entityManager
//    			.createQuery(query)
//    			.getResultList();
//    	
//    	return list;
    	
    	List<Person> list = null;
    	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Person> query = builder.createQuery(Person.class);
    	Root<Person> root = query.from(Person.class);
    	query.select(root).orderBy(builder.asc(root.get("name")));
    	
    	list = (List<Person>)entityManager
    			.createQuery(query)
    			.getResultList();
    	return list;
    	
    }
    
    @Override
    public Person findById(long id) {
    	return (Person)entityManager.createQuery("from Person where id = " + id).getSingleResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> findByName(String name){
    	return (List<Person>)entityManager.createQuery("from Person where name = '" + name + "'").getResultList();
    }
    
    
//    @SuppressWarnings("unchecked")  // ← 非チェックのキャスト警告を抑制
    @Override
    public List<Person> find(String fstr){
//    	List<Person> list = null;
//    	Query query = entityManager
//    			.createNamedQuery("findWithName")
//    			.setParameter("fname", "%" + fstr + "%");
//    	list = query.getResultList();
//    	return list;
    	
    	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Person> query = builder.createQuery(Person.class);
    	Root<Person> root = query.from(Person.class);
    	query.select(root).where(builder.equal(root.get("name"), fstr));
    	
    	List<Person> list = null;
    	
    	list = (List<Person>)entityManager.createQuery(query).getResultList();
    	
    	return list;
    	
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> findByAge(int min, int max){
    	return (List<Person>)entityManager
    			.createNamedQuery("findByAge")
    			.setParameter("min",min)
    			.setParameter("max",max)
    			.getResultList();
    }
    
    @Override
    public List<Person> getPage(int page, int limit){
    	int offset = page * limit;
    	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Person> query = builder.createQuery(Person.class);
    	Root<Person> root = query.from(Person.class);
    	query.select(root);
    	return (List<Person>)entityManager
    			.createQuery(query)
    			.setFirstResult(offset)
    			.setMaxResults(limit)
    			.getResultList();
    }
    
    
    
    
    
    
}
