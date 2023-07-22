mosheh
==========
[![Actions Status](https://github.com/geektcp/mosheh/actions/workflows/maven.yml/badge.svg)](https://github.com/geektcp/mosheh/actions)
[![Security Score](https://snyk.io/test/github/geektcp/mosheh/badge.svg)](https://snyk.io/test/github/geektcp/mosheh)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.geektcp.common/mosheh/badge.svg#)](https://mvnrepository.com/artifact/com.geektcp.common/mosheh)
[![OpenSSF Best Practices](https://bestpractices.coreinfrastructure.org/projects/7038/badge)](https://bestpractices.coreinfrastructure.org/projects/7038)
[![Java Doc](https://img.shields.io/badge/javadoc-6.0.5-brightgreen.svg)](https://javadoc.io/doc/com.geektcp.common/mosheh/latest/index.html)

### describe
```
mosheh is a common library for Java, which release in maven repository:
https://mvnrepository.com/artifact/com.geektcp.common/mosheh

this is a tiny project. we can use it to develop big project.

Furthermore, mosheh offers a convenient API for build many cache, collection, executor.
```

### maven dependency
```
<!-- https://mvnrepository.com/artifact/com.geektcp.common/mosheh -->
<dependency>
    <groupId>com.geektcp.common</groupId>
    <artifactId>mosheh</artifactId>
    <version>1.0.3</version>
</dependency>
```


### example
```java
package xxx;

/**
 * @author Geektcp 2021/9/23 14:30
 */
@Slf4j
@Service
public class RoomInfoServerImpl extends JpaBase implements RoomInfoServer {

    private LoadingCache<Object, List<RoomInfoVo>> tinyCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(7, TimeUnit.SECONDS)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new TinyLoader<Object, List<RoomInfoVo>>() {
                @Override
                public List<RoomInfoVo> load(Object key) {
                    return null;
                }
            });

    public List<RoomInfoVo> queryRoomInfo(RoomInfoQo qo) {
        log.info("qo: {}", JSON.toJSONString(qo));
        
        // get result from tiny cache
        List<RoomInfoVo> cacheResult =  roomCache.get(qo);
        if(Objects.nonNull(cacheResult)){
            return cacheResult;
        }

        // here is your code, for example: select mysql with JPA or Mybatis
        String title = qo.getTitle();
        String id = qo.getId();
        String author = qo.getAuthor();

        QRoomInfoPo qRoomInfoPo = QRoomInfoPo.roomInfoPo;
        JPQLQuery<RoomInfoPo> jpqlQuery = jpa.select(qRoomInfoPo).from(qRoomInfoPo);
        if(StringUtils.isNotEmpty(title)){
            jpqlQuery.where(qRoomInfoPo.title.like(JQL.likeWrap(title)));
        }
        if(StringUtils.isNotEmpty(id)){
            jpqlQuery.where(qRoomInfoPo.id.eq(id));
        }
        if(StringUtils.isNotEmpty(author)){
            jpqlQuery.where(qRoomInfoPo.author.like(JQL.likeWrap(author)));
        }
        List<RoomInfoPo> poList = jpqlQuery.orderBy(qRoomInfoPo.updateBy.asc()).fetch();

        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }
        List<RoomInfoVo> voList = poList.stream().map((RoomInfoPo po) -> {
            RoomInfoVo vo = new RoomInfoVo();
            BeanMapper.map(po, vo);
            return vo;
        }).collect(Collectors.toList());

        // add result to tiny cache
        roomCache.put(qo, voList);
        
        return voList;
    }

}

```