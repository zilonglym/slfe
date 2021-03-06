package com.selfwork.intelligence.controller;

import com.github.pagehelper.PageInfo;
import com.selfwork.intelligence.biz.DataBiz;
import com.selfwork.intelligence.biz.DataQualityBiz;
import com.selfwork.intelligence.biz.ExChangeConfigBiz;
import com.selfwork.intelligence.common.enums.AuditStatusEnum;
import com.selfwork.intelligence.common.enums.QualityEvaluateEnum;
import com.selfwork.intelligence.model.po.ExchangerPO;
import com.selfwork.intelligence.model.vo.dataquality.DataQualitVo;
import com.selfwork.intelligence.model.vo.dataquality.DataQualityQueryVo;
import com.selfwork.intelligence.model.vo.exchangeConfig.ExchangeConfigQueryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzc on 2018/4/22.
 * 交换配置
 */
@Controller
@RequestMapping(value = "/exchangeConfig")
public class ExchangeConfigController extends BaseController {
    public final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExChangeConfigBiz exChangeConfigBiz;

    @Autowired
    private DataBiz dataBiz;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("sub/exchangeConfig/index");
        view.addObject("auditStatusEnums", AuditStatusEnum.values());
        view.addObject("qualityEvaluateEnums", QualityEvaluateEnum.values());
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Map<String, Object> getList(@RequestBody ExchangeConfigQueryVo queryVo) {

        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("rows", new ArrayList());

        // 参数验证
        if (null == queryVo) {
            return result;
        }
        try {
            PageInfo<ExchangerPO> pageData = exChangeConfigBiz.findPage(queryVo);
            if (pageData != null) {
                result.put("total", pageData.getTotal());
                result.put("rows", pageData.getList());
            }
        } catch (Exception e) {
            logger.error("获取交换配置异常 e:{}" + e.getMessage(), e);
        }
        return result;
    }


    @RequestMapping("/toAdd")
    public ModelAndView toAdd() {
        ModelAndView view = new ModelAndView("sub/exchangeConfig/add");
        return view;
    }


    @RequestMapping("/toEdit")
    public ModelAndView toEdit(Integer id) {
        ModelAndView view = new ModelAndView("sub/exchangeConfig/edit");
        ExchangerPO exchangerPO = exChangeConfigBiz.findById(id);
        view.addObject("config",exchangerPO);
        return view;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@RequestBody ExchangerPO exchanger) {
        Map<String, Object> result = new HashMap<>();

        // 参数验证
        if (null == exchanger) {
            return result;
        }
        try {
            exChangeConfigBiz.save(exchanger,this.getLoginUser());
            result.put("code", 0);
            result.put("msg", "保存成功");
        } catch (Exception e) {
            logger.error("保存交换配置信息异常 e:{}", e);
            result.put("code", -1);
            result.put("msg", "保存交换配置信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> edit(@RequestBody ExchangerPO exchanger) {
        Map<String, Object> result = new HashMap<>();

        // 参数验证
        if (null == exchanger) {
            return result;
        }
        try {
            exChangeConfigBiz.edit(exchanger,this.getLoginUser());
            result.put("code", 0);
            result.put("msg", "修改成功");
        } catch (Exception e) {
            logger.error("修改交换配置信息异常 e:{}", e);
            result.put("code", -1);
            result.put("msg", "修改交换配置信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> test(@RequestBody ExchangerPO exchanger) {
        Map<String, Object> result = new HashMap<>();

        // 参数验证
        if (null == exchanger) {
            return result;
        }
        try {
            boolean success = dataBiz.test(exchanger);
            if(success){
                result.put("code", 0);
                result.put("msg", "连接成功");
            }else {
                result.put("code", -1);
                result.put("msg", "连接失败");
            }

        } catch (Exception e) {
            logger.error("修改交换配置信息异常 e:{}", e);
            result.put("code", -1);
            result.put("msg", "修改交换配置信息失败");
        }

        return result;
    }

}
