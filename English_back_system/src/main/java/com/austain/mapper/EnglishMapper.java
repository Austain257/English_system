package com.austain.mapper;

import com.austain.domain.po.AddRequest;
import com.austain.domain.po.Englishs;
import com.austain.domain.po.Sentence;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EnglishMapper {

    @Insert("insert into englishword4420(word,chinese,pronounce,times) values(#{word},#{chinese},#{pronounce},#{times})")
    void insert(Englishs englishs);

    @Select("select * from ${bookName} where id between #{startIndex} and  #{endIndex}")
    List<Englishs> getEnglishList(String startIndex,String endIndex,@Param("bookName") String bookName);

    @Update("update englishword575 set pronounce = #{pronounce} where word = #{word}")
//    @Update("update ${bookName} set pronounce = #{pronounce} where word = #{word}")
    void updatePronounce(String word, String pronounce);

    @Insert("insert into error_word_list(word,chinese,pronounce,bookname,user_id) values(#{word},#{chinese},#{pronounce},#{bookname},#{userId})")
    int addAgainWord(AddRequest request);

    @Delete("delete from error_word_list where word = #{word} and id = #{id}")
    int removeAgainWord(AddRequest request);

    @Select("select * from sentence200 where id between #{start} and #{end}")
    List<Sentence> getSentenceList(String start, String end);

    @Insert("insert into finally_again_word(word,chinese,pronounce,bookname,user_id) values(#{word},#{chinese},#{pronounce},#{bookname},#{userId})")
    int finalAddAgainWord(AddRequest request);

    // 错词本相关接口
    @Select("select * from error_word_list order by create_time desc")
    List<Englishs> getAllAgainWords();

    @Select("select * from error_word_list where bookname = #{bookname} order by create_time desc")
    List<Englishs> getAgainWordsByBook(@Param("bookname") String bookname);

    @Select("select * from error_word_list where times >= #{minTimes} order by times desc, create_time desc")
    List<Englishs> getAgainWordsByTimes(@Param("minTimes") int minTimes);

    @Update("update error_word_list set times = times + 1 where id = #{id}")
    int increaseWordTimes(@Param("id") Long id);

    @Select("select distinct bookname from error_word_list order by bookname")
    List<String> getAgainWordBooks();

    // 用户相关的错词查询方法
    @Select("select * from error_word_list where user_id = #{userId} order by create_time desc")
    List<Englishs> getAgainWordsByUserId(@Param("userId") Long userId);

    @Select("select * from error_word_list where bookname = #{bookname} and user_id = #{userId} order by create_time desc")
    List<Englishs> getAgainWordsByBookAndUserId(@Param("bookname") String bookname, @Param("userId") Long userId);

    @Select("select * from error_word_list where times >= #{minTimes} and user_id = #{userId} order by times desc, create_time desc")
    List<Englishs> getAgainWordsByTimesAndUserId(@Param("minTimes") int minTimes, @Param("userId") Long userId);

    @Select("select distinct bookname from error_word_list where user_id in (#{userId},2) order by bookname")
    List<String> getAgainWordBooksByUserId(@Param("userId") Long userId);

    @Select("select count(*) > 0 from error_word_list where id = #{wordId} and user_id = #{userId}")
    boolean isWordBelongsToUser(@Param("wordId") Long wordId, @Param("userId") Long userId);

    /**
     * 错词本分页查询（支持管理员查看全部）
     */
    @Select({
            "<script>",
            "select * from error_word_list",
            "where 1=1",
            "<if test='!isAdmin'> and user_id = #{userId}</if>",
            "<if test='bookname != null and bookname != \"all\"'> and bookname = #{bookname}</if>",
            "<if test='minTimes != null and minTimes &gt; 0'> and times &gt;= #{minTimes}</if>",
            "order by create_time desc",
            "</script>"
    })
    List<Englishs> pageWrongbook(@Param("userId") Long userId,
                                 @Param("isAdmin") boolean isAdmin,
                                 @Param("bookname") String bookname,
                                 @Param("minTimes") Integer minTimes);

    @Select({
            "<script>",
            "select count(*) from error_word_list",
            "where 1=1",
            "<if test='!isAdmin'> and user_id = #{userId}</if>",
            "<if test='bookname != null and bookname != \"all\"'> and bookname = #{bookname}</if>",
            "<if test='minTimes != null and minTimes &gt; 0'> and times &gt;= #{minTimes}</if>",
            "</script>"
    })
    long countWrongbook(@Param("userId") Long userId,
                        @Param("isAdmin") boolean isAdmin,
                        @Param("bookname") String bookname,
                        @Param("minTimes") Integer minTimes);

    @Select({
            "<script>",
            "select count(*) from error_word_list",
            "where 1=1",
            "<if test='!isAdmin'> and user_id = #{userId}</if>",
            "<if test='bookname != null and bookname != \"all\"'> and bookname = #{bookname}</if>",
            "and times &gt;= 3",
            "</script>"
    })
    long countFrequentWrongbook(@Param("userId") Long userId,
                                @Param("isAdmin") boolean isAdmin,
                                @Param("bookname") String bookname);

    @Select({
            "<script>",
            "select count(distinct bookname) from error_word_list",
            "where 1=1",
            "<if test='!isAdmin'> and user_id = #{userId}</if>",
            "<if test='bookname != null and bookname != \"all\"'> and bookname = #{bookname}</if>",
            "</script>"
    })
    long countBookWrongbook(@Param("userId") Long userId,
                            @Param("isAdmin") boolean isAdmin,
                            @Param("bookname") String bookname);

    @Select({
            "<script>",
            "select count(*) from error_word_list",
            "where DATE(create_time) = CURDATE()",
            "<if test='!isAdmin'> and user_id = #{userId}</if>",
            "<if test='bookname != null and bookname != \"all\"'> and bookname = #{bookname}</if>",
            "</script>"
    })
    long countTodayWrongbook(@Param("userId") Long userId,
                             @Param("isAdmin") boolean isAdmin,
                             @Param("bookname") String bookname);
}
