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

    @Insert("insert into againenglishword(word,chinese,pronounce,times,bookname,user_id) values(#{word},#{chinese},#{pronounce},#{times},#{bookname},#{userId})")
    int addAgainWord(AddRequest request);

    @Delete("delete from againenglishword where word = #{word} and id = #{id}")
    int removeAgainWord(AddRequest request);

    @Select("select * from sentence200 where id between #{start} and #{end}")
    List<Sentence> getSentenceList(String start, String end);

    @Insert("insert into finally_again_word(word,chinese,pronounce,times,bookname) values(#{word},#{chinese},#{pronounce},#{times},#{bookname})")
    int finalAddAgainWord(AddRequest request);

    // 错词本相关接口
    @Select("select * from finally_again_word order by create_time desc")
    List<Englishs> getAllAgainWords();

    @Select("select * from finally_again_word where bookname = #{bookname} order by create_time desc")
    List<Englishs> getAgainWordsByBook(@Param("bookname") String bookname);

    @Select("select * from finally_again_word where times >= #{minTimes} order by times desc, create_time desc")
    List<Englishs> getAgainWordsByTimes(@Param("minTimes") int minTimes);

    @Update("update finally_again_word set times = times + 1 where id = #{id}")
    int increaseWordTimes(@Param("id") Long id);

    @Select("select distinct bookname from againenglishword order by bookname")
    List<String> getAgainWordBooks();

    // 用户相关的错词查询方法
    @Select("select * from finally_again_word where user_id = #{userId} order by create_time desc")
    List<Englishs> getAgainWordsByUserId(@Param("userId") Long userId);

    @Select("select * from finally_again_word where bookname = #{bookname} and user_id = #{userId} order by create_time desc")
    List<Englishs> getAgainWordsByBookAndUserId(@Param("bookname") String bookname, @Param("userId") Long userId);

    @Select("select * from finally_again_word where times >= #{minTimes} and user_id = #{userId} order by times desc, create_time desc")
    List<Englishs> getAgainWordsByTimesAndUserId(@Param("minTimes") int minTimes, @Param("userId") Long userId);

    @Select("select distinct book_name from user_books where user_id in (#{userId},2) order by book_name")
    List<String> getAgainWordBooksByUserId(@Param("userId") Long userId);

    @Select("select count(*) > 0 from finally_again_word where id = #{wordId} and user_id = #{userId}")
    boolean isWordBelongsToUser(@Param("wordId") Long wordId, @Param("userId") Long userId);
}
