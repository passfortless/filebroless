// Default API
interface Api {
  protocol: string;
  host: string;
  port: string;
  prefix: string;
  version: string;
}

// Environment
export interface Environment {
  appVersion: string;
  dev: boolean;
  production: boolean;
  enableServiceWorker: boolean;
  navigateToErrorPage: boolean;
  logHttpErrors: boolean;
  requestDelay: number;
  api: Api;
}
