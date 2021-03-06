package api.data.controller;

import api.data.domain.XModel;
import api.data.domain.XModelStore;
import com.vue.adminlte4j.annotation.Form;
import com.vue.adminlte4j.model.UIModel;
import java.io.IOException;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjliuyong on 2018/3/30.
 */
@Controller
public class FormController  {

    @GetMapping("/test/form/get")
    @ResponseBody
    public UIModel get(){
        //just for test form
        XModel xModel = XModelStore.findOne() ;
        return  UIModel.success().formData(xModel , XModel.class) ;
    }

    @PostMapping("/test/form/update")
    @ResponseBody
    public UIModel update(@RequestBody XModel xModel) throws IOException {
        XModelStore.add(xModel);
        return  UIModel.success().setMsg("保存成功") ;
    }
}
