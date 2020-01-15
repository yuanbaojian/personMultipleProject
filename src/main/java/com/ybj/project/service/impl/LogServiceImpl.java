package com.ybj.project.service.impl;

import com.ybj.project.mapper.LogMapper;
import com.ybj.project.model.Log;
import com.ybj.project.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ybj
 * @since 2020-01-15
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
