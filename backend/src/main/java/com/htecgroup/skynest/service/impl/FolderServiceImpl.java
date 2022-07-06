package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.FolderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FolderServiceImpl implements FolderService {

  private FolderRepository folderRepository;
  private ModelMapper modelMapper;

  private ActionService actionService;

  @Override
  public void removeFolder(UUID uuid) {
    FolderDto folderDto = modelMapper.map(folderRepository.findFolderById(uuid), FolderDto.class);
    if (folderDto.isDeleted()) {
      throw new FolderAlreadyDeletedException();
    }
    FolderDto deletedFolderDto = folderDto.deleteFolder();
    FolderEntity folderEntity =
        folderRepository.save(modelMapper.map(deletedFolderDto, FolderEntity.class));
    actionService.recordAction(Collections.singleton(folderEntity), ActionType.DELETE);
  }

  @Override
  public FolderResponse getFolderDetails(UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);
    FolderResponse folderResponse = modelMapper.map(folderEntity, FolderResponse.class);
    return folderResponse;
  }
}
