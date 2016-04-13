package jp.co.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.dao.UserMasterDao;
import jp.co.entity.UserMaster;
import jp.co.model.IndexForm;

@Controller
@EnableAutoConfiguration
public class UserController {
	/** 情報用Dao */
	@Autowired
	private UserMasterDao userDao;

	/**
	 * 一覧画面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/", "/users" })
	public String index(Model model) {

		List<UserMaster> users = userDao.selectAll();
		model.addAttribute("users", users);

		return "home/index";
	}


	/**
	 * 登録画面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/users/new" })
	public String newUser(Model model){
		model.addAttribute("indexForm", new IndexForm());
		return "home/new";
	}




	/**
	 * DB書き込み
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/users/create" })
	public String create(@ModelAttribute IndexForm indexForm, Model model){

		//エンティティに値を受け渡し
		UserMaster um = new UserMaster();
		um.name = indexForm.getName();
		um.email = indexForm.getEmail();
		um.password = indexForm.getPassword();
		um.remarks = indexForm.getRemarks();
		userDao.insert(um);

		return index(model);
	}



}
