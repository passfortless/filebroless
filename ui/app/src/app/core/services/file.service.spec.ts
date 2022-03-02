import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { getTestBed, TestBed } from '@angular/core/testing';
import { AppConfigModule } from '@app/app-config.module';

import { FileService } from './file.service';

describe('FileService', () => {
  let injector: TestBed;
  let service: FileService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        AppConfigModule
      ],
      providers: [FileService]
    });

    injector = getTestBed();
    service = injector.inject(FileService);
    httpMock = injector.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // it('checkESignStatus$(id: string, templateid: string) should provide data', () => {
  //   service.checkESignStatus$('23423', '32432').subscribe((data: DataItem<ESignStatus>) => {
  //     expect(data).not.toBe(null);
  //   });
  //   const req = httpMock.expectOne(request => request.method === 'GET');
  //   expect(req.request.method).toBe('GET');
  //   req.flush({ message: 'success' });
  // });
});
