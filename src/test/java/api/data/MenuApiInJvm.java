package api.data;

import com.vue.adminlte4j.model.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bjliuyong on 2017/12/14.
 */
public class MenuApiInJvm {

    private static  List<Menu> menus ;

    static  {
        menus = buildMenu() ;
    }

    public static List<Menu> getMenu() {
        return  menus ;
    }

    private static List<Menu> buildMenu() {

        List<Menu> menus = new ArrayList<>() ;

        Menu menu1 = createMenu("/index.html" , "首页" , "fa fa-link" , 1) ;
        Menu menu2 = createMenu("#" , "实例" , "fa fa-link" , 2) ;
        Menu menu3 = createMenu("#" , "ui-element" , "fa fa-link" , 3) ;

        menu2.addChildMenu(createMenu("/example/v_box.html" , "box使用" , "fa fa-circle-o" , 1));
        menu2.addChildMenu(createMenu("/example/v_table.html" , "table使用" , "fa fa-circle-o" , 2));


        menu2.addChildMenu(createMenu("/example/v_button.html" , "button使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_layout.html" , "layout使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_alert.html" , "v_alert使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_callout.html" , "v_callout使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/custom_tab.html" , "custom_tab使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_text.html" , "text使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_folder.html" , "folder使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_tags.html" , "tags使用" , "fa fa-circle-o" , 2));
        menu2.addChildMenu(createMenu("/example/v_form.html" , "form表单" , "fa fa-circle-o" , 2));

        menu3.addChildMenu(createMenu("/ui-element/v_modal.html" , "Modal" , "fa fa-circle-o" , 1));
        menus.add(menu1) ;
        menus.add(menu2) ;
        menus.add(menu3) ;



        return menus;
    }

    private static Menu createMenu(String url , String desc , String icon ,int order) {
        Menu menu = new Menu() ;
        menu.setId(UUID.randomUUID().toString());
        menu.setUrl(url);
        menu.setDesc(desc);
        menu.setIcon(icon);
        menu.setOrder(order);
        return menu ;
    }
}
