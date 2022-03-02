import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { APP_CONFIG } from '@app/app-config.module';
import { AppConfig } from '@app/app.interface';
import { FileMetadata } from '@interfaces/filemetadata';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private serviceUrl: string;

  /**
   * @constructor
   * @param _http
   * @param _config
   */
  constructor(private _http: HttpClient, @Inject(APP_CONFIG) private readonly _config: AppConfig) {
    const { api: { host, suffix } } = this._config;
    this.serviceUrl = `${host}/${suffix}`;
  }

  /**
   * Load files
   *
   * @param directory
   */
  loadFiles$(directory: string): Observable<FileMetadata[]> {
    let params = new HttpParams();
    params = params.set('directory', directory);

    return this._http.get<FileMetadata[]>
      (`${this.serviceUrl}/files`, {
        params
      });
  }

  /**
   * Download file
   */
  downloadFile$(filePath: string): Observable<ArrayBuffer> {
    let params = new HttpParams();
    params = params.set('filePath', filePath);

    return this._http.get(`${this.serviceUrl}/files/`, { params, responseType: 'arraybuffer' });
  }
}
