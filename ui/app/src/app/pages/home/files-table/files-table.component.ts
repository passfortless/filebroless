import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { first } from 'rxjs/operators';

import { APP_CONFIG } from '@app/app-config.module';
import { AppConfig } from '@app/app.interface';
import { FilesTableFacade } from './files-table.facade';
import { FileMetadata } from '@interfaces/filemetadata';

@Component({
  selector: 'app-files-table',
  templateUrl: './files-table.component.html',
  styleUrls: ['./files-table.component.scss'],
  providers: [FilesTableFacade]
})
export class FilesTableComponent implements OnInit {

  directory!: string;
  fileMetadatas$!: Observable<FileMetadata[]>;

  constructor(
    @Inject(APP_CONFIG) private readonly _config: AppConfig,
    private _router: Router,
    private _route: ActivatedRoute,
    private _filesTableFacade: FilesTableFacade
  ) { }

  ngOnInit(): void {
    this.directory = this._route.snapshot.queryParams['dir'];
    if (!this.directory) {
      this.directory = '';
    }
    this.fileMetadatas$ = this._filesTableFacade.loadFiles$(this.directory);
  }

  /**
 *  List files in directory
 */
  listFolder(dir: string): void {
    this.directory = this.directory && this.directory.length > 0 ? this.directory + '/' + dir : dir;
    this._router.routeReuseStrategy.shouldReuseRoute = () => false;
    this._router.onSameUrlNavigation = 'reload';
    this._router.navigate([''], { queryParams: { dir: this.directory }, state: { reset: true } });
  }

  /**
   *  Download file
   */
  downloadFile(filename: string): void {
    this._filesTableFacade.downloadFile$(this.directory + '/' + filename)
      .pipe(first())
      .subscribe(
        result => {
          debugger;
          const blob = new Blob([result], { type: 'application/octet-stream' });
          const url = window.URL.createObjectURL(blob);
          // create hidden dom element (so it works in all browsers)
          const a = document.createElement('a');
          a.setAttribute('style', 'display:none;');
          document.body.appendChild(a);
          // create file, attach to hidden element and open hidden element
          a.href = url;
          a.target = '_blank';
          a.download = filename;
          a.click();
        }
      );
  }

  trackByFn(index: number): number {
    return index;
  }
}
