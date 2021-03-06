package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Response.CommonReturnType;
import com.example.demo.entity.Notice;
import com.example.demo.entity.Test;
import com.example.demo.entity.User;
import com.example.demo.entity.VO.StudentTestNoticeVO;
import com.example.demo.service.NoticeService;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    TestService testService;

    @RequestMapping("/one")
    public List<Test> listOne(@RequestParam String phone){
        QueryWrapper<Notice> queryWrapper= new QueryWrapper();
        queryWrapper.select("notice");
        return testService.list(new QueryWrapper<Test>().eq("phone",phone));
    }
    @RequestMapping
    public CommonReturnType notice(@RequestParam String phone) {
        //new CommonReturnType();
        QueryWrapper<Notice> queryWrapper= new QueryWrapper();
        queryWrapper.select("notice");
        List<Notice> note =noticeService.list(queryWrapper.eq("phone", phone));
        if(note!=null&&note.size() != 0){
            return CommonReturnType.create(note,"success");
        }else{
            return CommonReturnType.create(null,"暂无通知");
        }
    }
    @RequestMapping("/getStudentNotice")
    public Map<String, Object> getStudentNotice(@RequestParam String phone) {
        Map<String, Object> map = new HashMap<>();
        Page<StudentTestNoticeVO> Studentnote = noticeService.getStudentNote(new Page<>(),phone);
        if (Studentnote.getRecords().size() == 0) {
            map.put("message", "暂无数据");
        } else {
            map.put("message", "success");
            map.put("data", Studentnote);
        }
        return map;

    }
    @RequestMapping("/closenotice")
    public Map<String, Object> closenotice(@RequestParam String phone) {
        Map<String, Object> map = new HashMap<>();
        //new CommonReturnType();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "2017-01-01 11:11:11";
        Calendar calendar = Calendar.getInstance();
        long specialDate;
        long betweenDate = 0;
        long nowDate = calendar.getTime().getTime(); //Date.getTime() 获得毫秒型日期
        QueryWrapper<Test> queryWrapper = new QueryWrapper();
        queryWrapper.select("testtime","coursename","note");
        List<Test> note = testService.list(queryWrapper.eq("phone", phone));

        for (int i = 0; i < note.size(); i++) {
            try {
                specialDate = sdf.parse(note.get(i).getTesttime()).getTime();
                betweenDate = (specialDate - nowDate) / (1000 * 60 * 60 * 24);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (betweenDate > 7 || betweenDate<0) {
                note.remove(i);
            }
            else {
                note.get(i).setNote(String.valueOf(betweenDate));
            }
        }
        if (note.size() == 0) {
            map.put("message", "暂无数据");
        } else {
            map.put("message", "success");
            map.put("data", note);
        }
        return map;
    }




}

