package com.academicquest.repository;

import com.academicquest.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UploadRepository extends JpaRepository<Upload, Long>{
}
