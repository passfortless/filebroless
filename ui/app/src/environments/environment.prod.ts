import { Environment } from './environment.interface';

export const environment: Environment = {
  appVersion: 'v1.0.0',
  dev: true,
  production: false,
  enableServiceWorker: false,
  navigateToErrorPage: true,
  logHttpErrors: true,
  requestDelay: 0,
  api: {
    protocol: '',
    host: '',
    port: '',
    prefix: 'api',
    version: 'v1'
  }
};
