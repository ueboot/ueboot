
## 自定义分页查询作法
- 自定义一个Repository接口，在接口里面写自己的分页查询方法如
```$xslt

/**
 * 自定义分页多条件查询方法
 */
public interface ProjectCustomRepository {

    /**
     * 方法名任意，建议分页用paging开头
     * @param page 分页对象（包含第几页、每页数量）
     * @param name 其他条件。
     * @return 分页数据
     */
    Page<ProjectEntity> pagingBy(GridPage page, String name);
}

```
- 修改原有jpa的repository接口类，让这个接口继承自定义的接口
```$xslt
/**
 * 描述：项目仓储层
 *
 * @author yangkui create on 2017-02-28 下午4:36.
 * @since 1.0
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity,Long>,ProjectCustomRepository {

    ProjectEntity findById(Long id);
    ProjectEntity findByIdAndDeleteStatusIsNull(Long id);
    ProjectEntity findByName(String name);

    Page<ProjectEntity> findAll(Pageable pageable);

    List<ProjectEntity> findTop10ByCreateUserId(Long userId);

    List<ProjectEntity> findProjectByCreateUserId(Long createUserId);

}
```
- 添加一个RepositoryImpl实现类，这个类需要继承DefaultJpaRepository，并实现上面自定义的接口

```$xslt
/**
 * 描述:自定义查询实现
 *
 * @author yangkui create on 2017-04-20 上午11:38.
 * @since 1.0
 */
public class ProjectRepositoryImpl extends DefaultJpaRepository<ProjectEntity,Long> implements ProjectCustomRepository {
 
    /**
     * 方法名任意，建议分页用paging开头
     *
     * @param page 分页对象（包含第几页、每页数量）
     * @param name 其他条件。
     * @return 分页数据
     */
    @Override
    public Page<ProjectEntity> pagingBy(GridPage page, String name) {
        StringQuery query = StringQuery.newQuery()
                .query("from ProjectEntity a where a.deleted = false ")
                .predicateHasText(name)
                .query(" and a.name =:name")
                .param("name",name).build();

        return find(query, PageRequest.newPage(page.getPageNumber(),page.getPageSize()));
    }
}
```

- controller层接收查询条件，编写req类继承GridPage page属性
```$xslt
/**
 * 描述:历史消息分页
 *
 * @author yangkui create on 2017-04-09.
 * @since 1.0
 */
public class MessagePage extends GridPage {

    private Long sessionId;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
```
- contrller类如果需要将模型类转换后返回VO类，采用pages.map方式进行手工转换
```$xslt

    /**
     * 查询某一个会话的消息记录
     */
    @RequestMapping(value = "/findMessage")
    public Response<Page<ChatMessageVO>> findMessage(@RequestBody MessagePage page) {
        Page<ChatMessageEntity> pages = chatMessageService.pagingBy(page);
        Page<ChatMessageVO> result = pages.map(new Converter<ChatMessageEntity, ChatMessageVO>() {
            @Override
            public ChatMessageVO convert(ChatMessageEntity source) {
                ChatMessageVO vo = new ChatMessageVO();
                BeanUtils.copyProperties(source, vo);
                vo.setSessionId(source.getChatSession().getId());
                vo.setCsUserId(source.getCustomerServiceUser().getId());
                vo.setCreateTime(DateUtil.formatDate(source.getCreateTime(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS));
                return vo;
            }
        });
        return new Response<>(result);
    }
```

### service
- 依赖 repository模块
- 业务服务组件
- 所有类名以Service结尾。
- 根据entity包名定义每个Entity类对应的业务服务组件，实现类在同一个包名下的impl目录，并且是以Impl结尾

