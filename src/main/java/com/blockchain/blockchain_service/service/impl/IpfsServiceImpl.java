package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.dto.FileMetaData;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IpfsServiceImpl {
    private final IPFS ipfs;
    private final Map<String, FileMetaData> metadataMap = new HashMap<>();

    public FileMetaData uploadFile(MultipartFile file) throws IOException {
        NamedStreamable.InputStreamWrapper input = new NamedStreamable.InputStreamWrapper(file.getInputStream());
        List<MerkleNode> nodes = ipfs.add(input);
        String cid = nodes.get(0).hash.toString();

        String mimeType = file.getContentType();
        if (mimeType == null) {
            mimeType = URLConnection.guessContentTypeFromName(file.getOriginalFilename());
            if (mimeType == null) mimeType = "application/octet-stream";
        }

        FileMetaData meta = new FileMetaData(file.getOriginalFilename(), mimeType, cid);
        metadataMap.put(cid, meta);
        return meta;
    }

    public List<FileMetaData> uploadFiles(List<MultipartFile> files) throws IOException {
        List<FileMetaData> results = new ArrayList<>();

        for (MultipartFile file : files) {
            NamedStreamable.InputStreamWrapper stream =
                    new NamedStreamable.InputStreamWrapper(file.getInputStream());

            List<MerkleNode> nodes = ipfs.add(stream);
            String cid = nodes.get(0).hash.toString();

            String mimeType = file.getContentType();
            if (mimeType == null) {
                mimeType = URLConnection.guessContentTypeFromName(file.getOriginalFilename());
                if (mimeType == null) mimeType = "application/octet-stream";
            }

            results.add(new FileMetaData(file.getOriginalFilename(), mimeType, cid));
        }

        return results;
    }


    public byte[] getFile(String hash) throws IOException {
        Multihash filePointer = Multihash.fromBase58(hash);
        return ipfs.cat(filePointer);
    }


    public FileMetaData getMetadata(String cid) {
        return metadataMap.get(cid);
    }
}
