import { HttpClientTestingModule } from '@angular/common/http/testing';
import { inject, TestBed } from '@angular/core/testing';
import { AppConfigModule } from '@app/app-config.module';

import { FilesTableFacade } from './files-table.facade';

describe('Service: FilesTableFacade', () => {

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [
        AppConfigModule,
        HttpClientTestingModule
      ],
      providers: [
        FilesTableFacade
      ]
    });
  });

  it('should be created', inject([FilesTableFacade], (_facade: FilesTableFacade) => {
    expect(_facade).toBeTruthy();
  }));
});
