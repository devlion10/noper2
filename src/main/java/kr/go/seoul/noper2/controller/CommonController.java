package kr.go.seoul.noper2.controller;


import kr.go.seoul.noper2.dto.LedgerDTO;
import kr.go.seoul.noper2.dto.NoperOwnerDTO;
import kr.go.seoul.noper2.dto.NoperSiteChkDTO;
import kr.go.seoul.noper2.service.CommonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/common")
@Controller
public class CommonController {

    @RequestMapping("/juso")
    public String juso(Model model) {
        return "pages/common/juso";
    }

}
