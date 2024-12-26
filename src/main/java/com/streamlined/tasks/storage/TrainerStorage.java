package com.streamlined.tasks.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainer;

import jakarta.annotation.PostConstruct;

@Component
public class TrainerStorage {

	private final Map<Long, Trainer> trainerMap;
	private final PasswordEncoder passwordEncoder;

	public TrainerStorage(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		trainerMap = new HashMap<>();
	}

	@PostConstruct
	private void initialize() {
		List<Trainer> trainerList = List.of(
				new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", passwordEncoder.encode("idris"), true, "math"),
				new Trainer(2L, "Ken", "Artful", "Ken.Artful", passwordEncoder.encode("ken"), true, "art"),
				new Trainer(3L, "Hue", "Colorful", "Hue.Colorful", passwordEncoder.encode("hue"), true,
						"computer science"));
		for (Trainer trainer : trainerList) {
			trainerMap.put(trainer.getUserId(), trainer);
		}
	}

	public Trainer saveNew(Trainer trainer) {
		return trainerMap.putIfAbsent(trainer.getUserId(), trainer);
	}

	public void save(Long id, Trainer trainer) {
		trainerMap.put(id, trainer);
	}

	public Trainer get(Long id) {
		return trainerMap.get(id);
	}

	public void remove(Long id) {
		trainerMap.remove(id);
	}

	public Stream<Trainer> getAll() {
		return trainerMap.values().stream();
	}

}
