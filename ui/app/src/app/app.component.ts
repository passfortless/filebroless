import { Component, Inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { APP_CONFIG } from '@app/app-config.module';
import { AppConfig } from '@app/app.interface';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ui';
}
