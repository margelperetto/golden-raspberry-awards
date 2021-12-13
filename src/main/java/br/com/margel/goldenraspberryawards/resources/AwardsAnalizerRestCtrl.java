package br.com.margel.goldenraspberryawards.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.margel.goldenraspberryawards.business.AwardsAnalizer;
import br.com.margel.goldenraspberryawards.model.MinMax;

@RestController
public class AwardsAnalizerRestCtrl {

	@GetMapping("calc-min-max")
	public MinMax calcMinMax(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new AwardsAnalizer().calcMinMax();
	}

}