package com.app.mdc.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.app.mdc.model.system.Question;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-06-19
 */
public interface QuestionMapper extends BaseMapper<Question> {

	List<Question> exportWord(Map<String,Object> map);

}
