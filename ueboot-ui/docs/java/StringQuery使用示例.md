> 基于ueboot框架当中提供的StringQuery工具进行数据库查询示例

# 1.按条件分页查询
```java
public Page<Student> findBy(Pageable pageable, String teacherName) {
      StringQuery query = StringQuery.newQuery()
              .query ("select s from Student s, Teacher t where ")
              .query(" s.id = t.id ")
              .predicateHasText(teacherName)
              .query(" and t.name = :teacherName ")
              .param("teacherName", teacherName)
              .predicate(Boolean.TRUE).build();
      return find(query, pageable);
  }
```

# 2.leftjoin

```java

 public Page<Student> findByLeftJoin(Pageable pageable, String teacherName) {
        StringQuery query = StringQuery.newQuery()
                .query ("from Student s left join Teacher t ")
                .query(" on s.id = t.id ")
                .predicateHasText(teacherName)
                .query(" where t.name = :teacherName ")
                .param("teacherName", teacherName)
                .predicate(Boolean.TRUE)
                .query(" order by s.createdTime desc").build();
        return find(query, pageable);
    }

```

# 3.模糊查询
```jsp
 public Page<Student> findByPage(Pageable pageable, String name) {
        StringQuery query = StringQuery.newQuery()
                .query ("from Student s where ")
                .predicateHasText(name)
                .query(" s.name like :name ")
                .likeParam("name", name)
                .predicate(Boolean.TRUE).build();
        return find(query, pageable);
    }
```
# 4.HQL查询指定字段转对象

```jsp
 public Page<StudentDTO> findDTOByPage(Pageable pageable, String name) {
        StringQuery query = StringQuery.newQuery()
                .query ("select s.id as id, s.name as name, s.username as username, s.createdTime as createdTime from Student s where ")
                .predicateHasText(name)
                .query(" s.name like :name ")
                .likeParam("name", name)
                .predicate(Boolean.TRUE).build();
        return find(query, pageable, StudentDTO.class);
    }
```

# 5.SQL查询指定字段转对象

```jsp
public Page<StudentSqlDTO> findDTOByPageWithSql(Pageable pageable, String name) {
        StringQuery query = StringQuery.newQuery()
                .query ("select s.id as id, s.name as name, s.username as username, s.created_time as createdTime from t_student s where ")
                .predicateHasText(name)
                .query(" s.name like :name ")
                .likeParam("name", name)
                .predicate(Boolean.TRUE).build();
        return findBySql(query, pageable, StudentSqlDTO.class);
    }
```
```jsp
/**
     * SQL 查询结果转对象
     * @param name
     * @return
     */
    @Override
    public Page<Student> findByPageWithSql(Pageable pageable, String name) {
        StringQuery query = StringQuery.newQuery()
                .query ("select s.id, s.name, s.username, s.created_time from t_student s where ")
                .predicateHasText(name)
                .query(" s.name like :name ")
                .likeParam("name", name)
                .predicate(Boolean.TRUE)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("username", StandardBasicTypes.STRING)
                .addScalar("created_time", StandardBasicTypes.TIMESTAMP)
                .build();

        return findBySql(query, pageable, Student.class);
    }
```

```jsp
  /**
     * SQL 查询结果转对象
     * - 测试通过 注意 as createdTime
     * @param name
     * @return
     */
    @Override
    public Page<StudentSqlDTO> findDTOByPageWithSqlScalarType(Pageable pageable, String name) {
        StringQuery query = StringQuery.newQuery()
                .query ("select s.id, s.name, s.username as username, s.created_time as createdTime from t_student s where ")
                .predicateHasText(name)
                .query(" s.name like :name ")
                .likeParam("name", name)
                .predicate(Boolean.TRUE)
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("username", StandardBasicTypes.STRING)
                .addScalar("createdTime", StandardBasicTypes.TIMESTAMP)
                .build();

        return findBySql(query, pageable, StudentSqlDTO.class);
    }

```
```jsp
/**
     * HQL 查询结果转Map
     * - 测试通过
     * @param name
     * @return
     */
    @Override
    public Page<Map> findMapByPage(Pageable pageable, String name) {
        StringQuery query = StringQuery.newQuery()
                .query ("select s.id as id, s.name as name, s.username as username from Student s where ")
                .predicateHasText(name)
                .query(" s.name like :name ")
                .likeParam("name", name)
                .predicate(Boolean.TRUE).build();
        return find(query, pageable, Transformers.ALIAS_TO_ENTITY_MAP);
    }
```
