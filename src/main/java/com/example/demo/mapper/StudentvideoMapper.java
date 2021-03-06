package com.example.demo.mapper;

import com.example.demo.entity.Studentvideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.VO.StudentVideoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-03-12
 */
@Mapper
public interface StudentvideoMapper extends BaseMapper<Studentvideo> {
    @Select("SELECT user.`name`,user.`studentId`,user.`phone`,studentvideo.`video`,studentvideo.`time` FROM studentvideo,user WHERE studentvideo.phone=user.phone and user.studentId ="+"${studentId}")
    List<StudentVideoVO> getStudentPicture(/*Page<StudentTestNoticeVO> page*/String studentId);
}
