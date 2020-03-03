package com.app.mdc.serviceImpl.system;

import com.app.mdc.config.PicConfig;
import com.app.mdc.mapper.system.FileMapper;
import com.app.mdc.mapper.system.QuestionMapper;
import com.app.mdc.model.system.Question;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.QuestionService;
import com.app.mdc.utils.file.ExcelUtils;
import com.app.mdc.utils.jdbc.SqlUtils;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2019-06-19
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

	private final PicConfig picConfig;
	private final FileMapper fileMapper;

	public QuestionServiceImpl(PicConfig picConfig, FileMapper fileMapper) {
		this.picConfig = picConfig;
		this.fileMapper = fileMapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult addQuestion(Map<String, Object> map) {
		Question question=new Question();
		Date now=new Date();
		question.fromMap(map);
		question.setDeleted(0);
		question.setCreatetime(now);
		question.setUpdatetime(now);
		int count=this.baseMapper.insert(question);
		return count == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	public List<Question> getAll(Map<String, Object> map) {
		EntityWrapper<Question> entity=new EntityWrapper<>();
		//根据title模糊查询
		if(null != map.get("questionTitle") && !"".equals(map.get("questionTitle"))) {
			entity.like("question_title", map.get("questionTitle").toString());
		}
		//根据id可查某一个问题
		if(null != map.get("id")) {
			entity.eq("id", map.get("id").toString());
		}
		if(null != map.get("questionType") && !"".equals(map.get("questionType"))) {
			entity.eq("question_type", map.get("questionType").toString());
		}
		entity.eq("deleted", 0);
		entity.orderDesc(SqlUtils.orderBy("updatetime"));
		return this.baseMapper.selectList(entity);
	}

	@Override
	public Question getOne(Map<String, Object> map) {
		List<Question> list=getAll(map);
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult updateQuestion(Map<String, Object> map) {
		Question question=new Question();
		question.fromMap(map);
		question.setUpdatetime(new Date());
		int count=this.baseMapper.updateById(question);
		return count == 1 ? ResponseResult.success() : ResponseResult.fail();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult deleteQuestion(Map<String, Object> map) {
		//id是111,222,333格式
		if(null != map.get("id") && !"".equals(map.get("id"))){
			String id=map.get("id").toString();
			String ids[]=id.split(",");
			for (String string : ids){
				Question question=new Question();
				question.setId(string);
				question.setDeleted(1);
				question.setUpdatetime(new Date());
				this.baseMapper.updateById(question);
			}
		}
		return ResponseResult.success();
	}

	@Override
	public ResponseResult exportWord(Map<String, Object> map, User user) {
		//生成文件路径
		String path=picConfig.getSavePath()+"export"+"\\"+"question";
		File file=new File(path);
		if (!file.exists()){
			file.mkdirs();
		}

		//文件里面的数据
		String[] questionTypes=map.get("questionType").toString().split(",");
		map.put("questionTypes",questionTypes);
		List<Question> list=this.baseMapper.exportWord(map);
		//用日期作为文件名
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSS");
		String wordName = simpleDateFormat.format(new Date());
		//把问题，答案等按照html的规则拼接在一起
		StringBuilder content=new StringBuilder();
		content.append("<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
				"xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\"\n" +
				"xmlns=\"http://www.w3.org/TR/REC-html40\"><head>\n" +
				"<!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val=\"Cambria Math\"/><m:brkBin m:val=\"before\"/><m:brkBinSub m:val=\"--\"/><m:smallFrac m:val=\"off\"/><m:dispDef/><m:lMargin m:val=\"0\"/> <m:rMargin m:val=\"0\"/><m:defJc m:val=\"centerGroup\"/><m:wrapIndent m:val=\"1440\"/><m:intLim m:val=\"subSup\"/><m:naryLim m:val=\"undOvr\"/></m:mathPr></w:WordDocument></xml><![endif]-->\n" +
				"</head>");
		for (int i = 0; i <list.size() ; i++) {
			Question question=list.get(i);
			content.append("<p>"+(i+1)+"："+question.getQuestionTitle()+"</p>"+"<p>设备类型："+question.getQuestionType()
				+"</p>"+"解决方案："+question.getQuestionSolution()+"<br/>");
		}
		content.append("</html>");
		ExcelUtils.exportWord(wordName,content.toString(),path);

		//把生成的文件放到数据库file表里面，并把id返还给前端
		com.app.mdc.model.system.File file1=new com.app.mdc.model.system.File();
		Date date=new Date();
		file1.setCreatetime(date);
		file1.setUpdatetime(date);
		file1.setDeleted(0);
		file1.setFilename(wordName+".docx");
		file1.setFilepath("export/question/"+wordName+".docx");
		file1.setFiletype("docx");
		file1.setUserid(user.getId());
		file1.setMoudle("question");
		fileMapper.insert(file1);
		String fileId=file1.getId();

		return ResponseResult.success().add(fileId);
	}

}
