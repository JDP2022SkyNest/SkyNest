package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.service.FolderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FolderServiceImpl implements FolderService {

  private ModelMapper modelMapper;
  private FolderRepository folderRepository;

  @Override
  public FolderResponse getFolderDetails(UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);
    FolderResponse folderResponse = modelMapper.map(folderEntity, FolderResponse.class);
    return folderResponse;
  }

  @Override
  public List<FolderResponse> getAllRootFolders(UUID bucketId) {
    List<FolderEntity> allFolders =
        folderRepository.findAllByBucketIdAndParentFolderIsNull(bucketId);
    return getFolders(allFolders);
  }

  @Override
  public List<FolderResponse> getAllFoldersFromParent(UUID parentFolderId) {
    List<FolderEntity> allFolders = folderRepository.findAllByParentFolderId(parentFolderId);
    return getFolders(allFolders);
  }

  private List<FolderResponse> getFolders(List<FolderEntity> allFolders) {
    return allFolders.stream()
        .map(folder -> modelMapper.map(folder, FolderResponse.class))
        .collect(Collectors.toList());
  }
}
