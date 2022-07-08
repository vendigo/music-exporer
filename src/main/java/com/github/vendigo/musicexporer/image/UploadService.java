package com.github.vendigo.musicexporer.image;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.gcp.core.GcpProjectIdProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UploadService {

    private static final String LINK_DOMAIN = "https://storage.googleapis.com";
    private final Storage storage;
    private final GcpProjectIdProvider projectIdProvider;

    @SneakyThrows
    public String uploadFile(String fileName, byte[] fileContent) {
        String bucketName = projectIdProvider.getProjectId() + "-img";
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, fileContent);
        return String.join("/", LINK_DOMAIN, bucketName, fileName);
    }
}
