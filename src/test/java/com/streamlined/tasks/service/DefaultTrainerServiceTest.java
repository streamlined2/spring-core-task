package com.streamlined.tasks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TrainerMapper;
import com.streamlined.tasks.repository.TrainerRepository;

@ExtendWith(MockitoExtension.class)
class DefaultTrainerServiceTest {

	@Mock
	private TrainerRepository trainerRepository;

	@Spy
	private TrainerMapper trainerMapper;

	@Mock
	private SecurityService securityService;

	@InjectMocks
	private DefaultTrainerService trainerService;

	@Test
	@DisplayName("create adds new entity")
	void testCreate_shouldCreateNewEntity_ifCreateSucceeds() {
		Trainer trainer = new Trainer(5L, "Kyle", "Stark", "Kyle.Stark", "", true, "Math");
		TrainerDto trainerDto = trainerMapper.toDto(trainer);
		final char[] password = "password".toCharArray();
		List<Trainer> trainerList = new ArrayList<>(
				List.of(new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology"),
						new Trainer(2L, "Jack", "Powell", "Jack.Powell", "", true, "Geography"),
						new Trainer(3L, "Robert", "Orwell", "Robert.Orwell", "", true, "Physics"),
						new Trainer(4L, "Tingrid", "Kim", "Tingrid.Kim", "", false, "Chemistry")));
		final int initialTrainerListSize = trainerList.size();

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				trainerList.add(trainer);
				return null;
			}
		}).when(trainerRepository).create(trainer);

		trainerService.create(trainerDto, password);

		assertEquals(initialTrainerListSize + 1, trainerList.size());
		assertTrue(trainerList.contains(trainer));
		Optional<Trainer> newTrainer = trainerList.stream().filter(t -> t.getUserId().equals(trainer.getUserId()))
				.findFirst();
		assertTrue(newTrainer.isPresent());
		assertEquals(trainerDto, trainerMapper.toDto(newTrainer.get()));
		verify(trainerRepository).create(trainer);
	}

	@Test
	@DisplayName("create throws exception if trainer repository throws exception")
	void testCreate_shouldThrowEntityCreationException_ifTrainerRepositoryThrowsException() {
		Trainer trainer = new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Math");
		TrainerDto trainerDto = trainerMapper.toDto(trainer);
		final String errorMessage = "exception prevented new trainer entity creation";
		final char[] password = "password".toCharArray();

		doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(trainerRepository).create(trainer);

		Exception e = assertThrows(EntityCreationException.class, () -> trainerService.create(trainerDto, password));
		assertEquals("Error creating trainer entity", e.getMessage());
		assertEquals(errorMessage, e.getCause().getMessage());
		verify(trainerRepository).create(trainer);
	}

	@Test
	@DisplayName("update updates entity data for given trainer entity")
	void testUpdate_shouldUpdateEntityData_ifTrainerFoundAndUpdateSucceeds() {
		final int trainerIndex = 3;
		Trainer trainer = new Trainer(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, "Chemistry");
		TrainerDto trainerDto = trainerMapper.toDto(trainer);
		List<Trainer> trainerList = new ArrayList<>(
				List.of(new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology"),
						new Trainer(2L, "Jack", "Powell", "Jack.Powell", "", true, "Geography"),
						new Trainer(3L, "Robert", "Orwell", "Robert.Orwell", "", true, "Physics"),
						new Trainer(4L, "Tingrid", "Kim", "Tingrid.Kim", "", false, "Chemistry"),
						new Trainer(5L, "Kyle", "Stark", "Kyle.Stark", "", true, "Math")));

		when(trainerRepository.findById(trainer.getUserId())).thenReturn(Optional.of(trainer));
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				trainerList.set(trainerIndex, trainer);
				return null;
			}
		}).when(trainerRepository).update(trainer);

		trainerService.update(trainerDto);

		assertEquals(trainerDto, trainerMapper.toDto(trainerList.get(trainerIndex)));
		verify(trainerRepository).findById(trainer.getUserId());
		verify(trainerRepository).update(trainer);
	}

	@Test
	@DisplayName("update throws exception if trainer repository throws exception")
	void testUpdate_shouldThrowEntityUpdateException_ifTrainerRepositoryThrowsException() {
		Trainer trainer = new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology");
		TrainerDto trainerDto = trainerMapper.toDto(trainer);
		final String errorMessage = "exception prevented update";

		when(trainerRepository.findById(trainer.getUserId())).thenReturn(Optional.of(trainer));
		doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(trainerRepository).update(trainer);

		Exception e = assertThrows(EntityUpdateException.class, () -> trainerService.update(trainerDto));
		assertEquals("Error updating trainer entity", e.getMessage());
		assertEquals(errorMessage, e.getCause().getMessage());
		verify(trainerRepository).findById(trainer.getUserId());
		verify(trainerRepository).update(trainer);
	}

	@Test
	@DisplayName("update throws exception if trainer not found")
	void testUpdate_shouldThrowEntityUpdateException_ifTrainerNotFound() {
		Trainer trainer = new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology");
		TrainerDto trainerDto = trainerMapper.toDto(trainer);
		final String errorMessage = "No trainer entity with id %d".formatted(trainer.getUserId());

		when(trainerRepository.findById(trainer.getUserId())).thenReturn(Optional.empty());

		Exception e = assertThrows(EntityUpdateException.class, () -> trainerService.update(trainerDto));
		assertEquals("Error updating trainer entity", e.getMessage());
		assertEquals(errorMessage, e.getCause().getMessage());
		verify(trainerRepository).findById(trainer.getUserId());
		verify(trainerRepository, never()).update(trainer);
	}

	@Test
	@DisplayName("updatePassword updates password for given trainer entity")
	void testUpdatePassword_shouldUpdatePassword_ifTrainerFoundAndUpdateSucceeds() {
		final int trainerIndex = 3;
		Trainer trainer = new Trainer(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, "Chemistry");
		List<Trainer> trainerList = new ArrayList<>(
				List.of(new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Math"),
						new Trainer(2L, "Jack", "Powell", "Jack.Powell", "", true, "Biology"),
						new Trainer(3L, "Robert", "Orwell", "Robert.Orwell", "", true, "Geography"),
						new Trainer(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, "Physics"),
						new Trainer(5L, "Kyle", "Stark", "Kyle.Stark", "", true, "Chemistry")));
		final String password = "password";

		when(trainerRepository.findById(trainer.getUserId())).thenReturn(Optional.of(trainer));
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Trainer foundTrainer = trainerList.get(trainerIndex);
				foundTrainer.setPasswordHash(password);
				return null;
			}
		}).when(trainerRepository).update(trainer);

		trainerService.updatePassword(trainer.getUserId(), password.toCharArray());

		assertEquals(password, trainerList.get(trainerIndex).getPasswordHash());
		verify(trainerRepository).findById(trainer.getUserId());
		verify(trainerRepository).update(trainer);
	}

	@Test
	@DisplayName("updatePassword throws exception if trainer repository throws exception")
	void testUpdatePassword_shouldThrowEntityUpdateException_ifTrainerRepositoryThrowsException() {
		Trainer trainer = new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology");
		final String errorMessage = "exception prevented password update";
		final char[] password = "password".toCharArray();

		when(trainerRepository.findById(trainer.getUserId())).thenReturn(Optional.of(trainer));
		doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(trainerRepository).update(trainer);

		Exception e = assertThrows(EntityUpdateException.class,
				() -> trainerService.updatePassword(trainer.getUserId(), password));
		assertEquals("Error updating password for trainer entity", e.getMessage());
		assertEquals(errorMessage, e.getCause().getMessage());
		verify(trainerRepository).findById(trainer.getUserId());
		verify(trainerRepository).update(trainer);
	}

	@Test
	@DisplayName("updatePassword throws exception if trainer not found")
	void testUpdatePassword_shouldThrowEntityUpdateException_ifTrainerNotFound() {
		Trainer trainer = new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology");
		final String errorMessage = "No trainer entity with id %d".formatted(trainer.getUserId());
		final char[] password = "password".toCharArray();

		when(trainerRepository.findById(trainer.getUserId())).thenReturn(Optional.empty());

		Exception e = assertThrows(EntityUpdateException.class,
				() -> trainerService.updatePassword(trainer.getUserId(), password));
		assertEquals("Error updating password for trainer entity", e.getMessage());
		assertEquals(errorMessage, e.getCause().getMessage());
		verify(trainerRepository).findById(trainer.getUserId());
		verify(trainerRepository, never()).update(trainer);
	}

	@Test
	@DisplayName("deleteById deletes entity if trainer with given id exists")
	void testDeleteById_shouldDeleteTrainerEntity_ifTrainerWithGivenIdExists() {
		Trainer trainer = new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology");
		List<Trainer> trainerList = new ArrayList<>(
				List.of(new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology"),
						new Trainer(2L, "Jack", "Powell", "Jack.Powell", "", true, "Geography"),
						new Trainer(3L, "Robert", "Orwell", "Robert.Orwell", "", true, "Physics"),
						new Trainer(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, "Chemistry"),
						new Trainer(5L, "Kyle", "Stark", "Kyle.Stark", "", true, "Math")));
		final int initialListSize = trainerList.size();

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				trainerList.remove(trainer);
				return null;
			}
		}).when(trainerRepository).deleteById(trainer.getUserId());

		trainerService.deleteById(trainer.getUserId());

		assertFalse(trainerList.contains(trainer));
		assertEquals(initialListSize - 1, trainerList.size());
		verify(trainerRepository).deleteById(trainer.getUserId());
	}

	@Test
	@DisplayName("deleteById throws exception if trainer with given id does not exist")
	void testDeleteById_shouldThrowEntityDeletionException_ifTrainerWithGivenIdDoesNotExist() {
		Long nonExistentId = 1L;
		doAnswer(new ThrowsException(
				new NoSuchEntityException("No entity found with id %d".formatted(nonExistentId.intValue()))))
				.when(trainerRepository).deleteById(nonExistentId);

		Exception e = assertThrows(EntityDeletionException.class, () -> trainerService.deleteById(nonExistentId));
		assertEquals("Error deleting trainer entity", e.getMessage());
		assertEquals("No entity found with id %d".formatted(nonExistentId.intValue()), e.getCause().getMessage());
		verify(trainerRepository).deleteById(nonExistentId);
	}

	@Test
	@DisplayName("deleteById throws exception if trainer repository throws exception")
	void testDeleteById_shouldThrowEntityDeletionException_ifTrainerRepositoryThrowsException() {
		Long nonExistentId = 1L;
		String errorMessage = "cannot delete entity";
		doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(trainerRepository)
				.deleteById(nonExistentId);

		Exception e = assertThrows(EntityDeletionException.class, () -> trainerService.deleteById(nonExistentId));
		assertEquals("Error deleting trainer entity", e.getMessage());
		assertEquals(errorMessage, e.getCause().getMessage());
		verify(trainerRepository).deleteById(nonExistentId);
	}

	@Test
	@DisplayName("findById fetches trainer entity for given id")
	void testFindById_shouldReturnFoundTrainerEntity_ifPassedIdValidAndExists() {
		Long trainerId = 3L;
		Trainer expectedTrainer = new Trainer(trainerId, "Robert", "Orwell", "Robert.Orwell", "", true, "Physics");
		when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(expectedTrainer));

		Optional<TrainerDto> actualTrainerDto = trainerService.findById(trainerId);
		TrainerDto expectedTrainerDto = trainerMapper.toDto(expectedTrainer);

		verify(trainerRepository).findById(trainerId);
		assertTrue(actualTrainerDto.isPresent());
		assertEquals(expectedTrainerDto, actualTrainerDto.get());
	}

	@Test
	@DisplayName("findById returns empty Optional if given id does not exist")
	void testFindById_shouldReturnEmptyOptional_ifPassedIdDoesNotExist() {
		Long trainerId = 1L;
		when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

		Optional<TrainerDto> actualTrainerDto = trainerService.findById(trainerId);

		verify(trainerRepository).findById(trainerId);
		assertTrue(actualTrainerDto.isEmpty());
	}

	@Test
	@DisplayName("findById throws exception if trainer repository throws exception")
	void testFindById_shouldThrowEntityQueryException_ifTrainerRepositoryThrowsException() {
		Long nonExistentId = 1L;
		when(trainerRepository.findById(anyLong())).thenThrow(EntityQueryException.class);

		assertThrows(EntityQueryException.class, () -> trainerService.findById(nonExistentId));
		verify(trainerRepository).findById(anyLong());
	}

	@Test
	@DisplayName("findAll fetches all available trainer entities")
	void testFindAll_shouldReturnAllAvailableTrainerEntities_ifTrainerRepositoryReturnsResult() {
		List<Trainer> expectedTrainerList = List.of(new Trainer(1L, "John", "Smith", "John.Smith", "", true, "Biology"),
				new Trainer(2L, "Jack", "Powell", "Jack.Powell", "", true, "Geography"),
				new Trainer(3L, "Robert", "Orwell", "Robert.Orwell", "", true, "Physics"),
				new Trainer(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, "Chemistry"),
				new Trainer(5L, "Kyle", "Stark", "Kyle.Stark", "", true, "Math"));

		when(trainerRepository.findAll()).thenReturn(expectedTrainerList.stream());

		List<TrainerDto> resultingDtoList = trainerService.findAll().toList();
		List<TrainerDto> expectedDtoList = expectedTrainerList.stream().map(trainerMapper::toDto).toList();

		assertEquals(expectedDtoList, resultingDtoList);
		verify(trainerRepository).findAll();
	}

	@Test
	@DisplayName("findAll throws exception if trainer repository throws exception")
	void testFindAll_shouldThrowEntityQueryException_ifTrainerRepositoryThrowsException() {
		when(trainerRepository.findAll()).thenThrow(RuntimeException.class);

		assertThrows(EntityQueryException.class, () -> trainerService.findAll());
		verify(trainerRepository).findAll();
	}

}
