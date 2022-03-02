import { InjectionToken, NgModule } from '@angular/core';

import { APP_DI_CONFIG } from './app.config';
import { AppConfig } from './app.interface';

// Injection token
export let APP_CONFIG = new InjectionToken<AppConfig>('app.config');

@NgModule({
  providers: [{
    provide: APP_CONFIG,
    useValue: APP_DI_CONFIG
  }]
})
export class AppConfigModule {}
