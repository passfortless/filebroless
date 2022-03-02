import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { FileService } from '@app/core/services/file.service';
import { FileMetadata } from '@interfaces/filemetadata';

@Injectable()
export class FilesTableFacade {

  /**
   * @constructor
   */
  constructor(
    private _fileService: FileService) { }

  /**
   *  Load files for selected directory
   */
  loadFiles$(directory: string): Observable<FileMetadata[]> {
    return this._fileService.loadFiles$(directory);
  }

  /**
   *  Download file
   */
  downloadFile$(filePath: string): Observable<ArrayBuffer> {
    return this._fileService.downloadFile$(filePath);
  }

}
