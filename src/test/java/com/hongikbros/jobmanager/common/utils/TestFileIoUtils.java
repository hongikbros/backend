package com.hongikbros.jobmanager.common.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.hongikbros.jobmanager.common.utils.exception.NotCreateBytesFromFilePath;

public class TestFileIoUtils {
    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = Paths.get(Objects.requireNonNull(
                    TestObjectUtils.class.getResource(filePath),
                    "파일 이름이 잘못됐습니다.")
                    .toURI());
            return Files.readAllBytes(path);
        } catch (URISyntaxException | IOException e) {
            throw new NotCreateBytesFromFilePath("파일을 만드는데 실패했습니다");
        }
    }
}
