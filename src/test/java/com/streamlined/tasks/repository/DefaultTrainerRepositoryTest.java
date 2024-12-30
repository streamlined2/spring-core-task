package com.streamlined.tasks.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.storage.TrainerStorage;

@ExtendWith(MockitoExtension.class)
class DefaultTrainerRepositoryTest {

	@Spy
	private TrainerStorage trainerStorage;

	@InjectMocks
	private DefaultTrainerRepository trainerRepository;

	@Test
	@DisplayName("create should create new trainer entity if entity with such id does not exist")
	void testCreate_shouldCreateNewTrainer_ifTrainerEntityWithSuchIdDoesNotExist() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");

		trainerRepository.create(trainer);

		assertEquals(1, trainerStorage.size());
		Trainer savedTrainer = trainerStorage.get(trainer.getUserId());
		assertNotNull(savedTrainer);
		assertEquals(trainer.toString(), savedTrainer.toString());
	}

	@Test
	@DisplayName("create should throw exception if entity with such id exists")
	void testCreate_shouldThrowException_ifTrainerEntityWithSuchIdExists() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);

		assertThrows(EntityAlreadyExistsException.class, () -> trainerRepository.create(trainer));
		assertEquals(1, trainerStorage.size());
		Trainer savedTrainer = trainerStorage.get(trainer.getUserId());
		assertNotNull(savedTrainer);
		assertEquals(trainer.toString(), savedTrainer.toString());
	}

	@Test
	@DisplayName("update should update trainer entity data if entity with such id exists")
	void testUpdate_shouldUpdateTrainerEntityData_ifTrainerEntityWithSuchIdExists() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);
		Trainer updatedTrainer = new Trainer(1L, "Ken", "Artful", "Ken.Artful", "", true, "art");

		trainerRepository.update(updatedTrainer);

		assertEquals(1, trainerStorage.size());
		Trainer savedTrainer = trainerStorage.get(trainer.getUserId());
		assertNotNull(savedTrainer);
		assertEquals(updatedTrainer.toString(), savedTrainer.toString());
	}

	@Test
	@DisplayName("update should add new trainer entity if entity with such id does not exist")
	void testUpdate_shouldAddNewTrainerEntity_ifTrainerEntityWithSuchIdDoesNotExist() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);
		Trainer updatedTrainer = new Trainer(2L, "Ken", "Artful", "Ken.Artful", "", true, "art");

		trainerRepository.update(updatedTrainer);

		assertEquals(2, trainerStorage.size());
		Trainer savedTrainer = trainerStorage.get(trainer.getUserId());
		assertNotNull(savedTrainer);
		assertEquals(trainer.toString(), savedTrainer.toString());
		savedTrainer = trainerStorage.get(updatedTrainer.getUserId());
		assertNotNull(savedTrainer);
		assertEquals(updatedTrainer.toString(), savedTrainer.toString());
	}

	@Test
	@DisplayName("deleteById should delete trainer entity if entity with such id exists")
	void testDeleteById_shouldDeleteTrainerEntity_ifTrainerEntityWithSuchIdExists() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);

		trainerRepository.deleteById(trainer.getUserId());

		assertEquals(0, trainerStorage.size());
	}

	@Test
	@DisplayName("deleteById should throw exception if entity with such id does not exist")
	void testDeleteById_shouldThrowException_ifTrainerEntityWithSuchIdDoesNotExist() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);
		Trainer trainerToBeDeleted = new Trainer(2L, "Ken", "Artful", "Ken.Artful", "", true, "art");

		assertThrows(NoSuchEntityException.class, () -> trainerRepository.deleteById(trainerToBeDeleted.getUserId()));

		assertEquals(1, trainerStorage.size());
		Trainer savedTrainer = trainerStorage.get(trainer.getUserId());
		assertNotNull(savedTrainer);
		assertEquals(trainer.toString(), savedTrainer.toString());
	}

	@Test
	@DisplayName("findById should return trainer entity wrapped in Optional if entity with such id exists")
	void testFindById_shouldReturnTrainerEntity_ifTrainerEntityWithSuchIdExists() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);

		Optional<Trainer> foundTrainer = trainerRepository.findById(trainer.getUserId());

		assertTrue(foundTrainer.isPresent());
		assertSame(trainer, foundTrainer.get());
	}

	@Test
	@DisplayName("findById should return empty Optional if entity with such id does not exist")
	void testFindById_shouldReturnEmptyOptional_ifTrainerEntityWithSuchIdDoesNotExist() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);
		Trainer trainerToLookup = new Trainer(2L, "Ken", "Artful", "Ken.Artful", "", true, "art");

		Optional<Trainer> foundTrainer = trainerRepository.findById(trainerToLookup.getUserId());

		assertTrue(foundTrainer.isEmpty());
	}

	@Test
	@DisplayName("findAll should return all trainer entities storage contains")
	void testFindAll_shouldReturnAllTrainerEntitiesStorageContains() {
		trainerStorage.clear();
		Trainer trainer1 = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer1);
		Trainer trainer2 = new Trainer(2L, "Ken", "Artful", "Ken.Artful", "", true, "art");
		trainerStorage.saveNew(trainer2);

		List<Trainer> foundTrainers = trainerRepository.findAll().toList();

		assertEquals(2, foundTrainers.size());
		Trainer foundTrainer = trainerStorage.get(trainer1.getUserId());
		assertNotNull(foundTrainer);
		assertEquals(trainer1.toString(), foundTrainer.toString());
		foundTrainer = trainerStorage.get(trainer2.getUserId());
		assertNotNull(foundTrainer);
		assertEquals(trainer2.toString(), foundTrainer.toString());
	}

	@Test
	@DisplayName("getMaxUsernameSerial should return empty optional if given user name does not exist in storage")
	void testGetMaxUsernameSerial_shouldReturnEmptyOptional_ifGivenUserNameDoesNotExistInStorage() {
		trainerStorage.clear();

		assertTrue(trainerRepository.getMaxUsernameSerial("Idris", "Powerful").isEmpty());
	}

	@Test
	@DisplayName("getMaxUsernameSerial should return empty string if given user name present in storage once")
	void testGetMaxUsernameSerial_shouldReturnEmptyString_ifGivenUserNamePresentInStorageOnce() {
		trainerStorage.clear();
		Trainer trainer = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer);

		Optional<String> serialNumber = trainerRepository.getMaxUsernameSerial("Idris", "Powerful");
		assertTrue(serialNumber.isPresent());
		assertTrue(serialNumber.get().isEmpty());
	}

	@Test
	@DisplayName("getMaxUsernameSerial should return 1 if given user name present in storage twice")
	void testGetMaxUsernameSerial_shouldReturn1_ifGivenUserNamePresentInStorageTwice() {
		trainerStorage.clear();
		Trainer trainer1 = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer1);
		Trainer trainer2 = new Trainer(2L, "Idris", "Powerful", "Idris.Powerful1", "", true, "math");
		trainerStorage.saveNew(trainer2);

		Optional<String> serialNumber = trainerRepository.getMaxUsernameSerial("Idris", "Powerful");
		assertTrue(serialNumber.isPresent());
		assertEquals(1, Long.valueOf(serialNumber.get()));
	}

	@Test
	@DisplayName("getMaxUsernameSerial should return 2 if given user name present in storage thrice")
	void testGetMaxUsernameSerial_shouldReturn2_ifGivenUserNamePresentInStorageThrice() {
		trainerStorage.clear();
		Trainer trainer1 = new Trainer(1L, "Idris", "Powerful", "Idris.Powerful", "", true, "math");
		trainerStorage.saveNew(trainer1);
		Trainer trainer2 = new Trainer(2L, "Idris", "Powerful", "Idris.Powerful1", "", true, "math");
		trainerStorage.saveNew(trainer2);
		Trainer trainer3 = new Trainer(3L, "Idris", "Powerful", "Idris.Powerful2", "", true, "math");
		trainerStorage.saveNew(trainer3);

		Optional<String> serialNumber = trainerRepository.getMaxUsernameSerial("Idris", "Powerful");
		assertTrue(serialNumber.isPresent());
		assertEquals(2, Long.valueOf(serialNumber.get()));
	}

}
