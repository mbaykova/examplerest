package demo;

import java.util.List;

/**
 * Created by Maria on 06.06.2018.
 */
public interface IService {

	public List<Person> findAll();
	public Person findById(Long id);
	public int updateById(Integer status, String name, Long id);
	public int deleteById(Integer status, String name, Long id);
	public Person add(Integer status, String name, Long id);
}
