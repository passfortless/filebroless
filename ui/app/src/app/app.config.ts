// Application level constants and configuration
import { environment } from '@env/environment';

import { AppConfig } from './app.interface';

// Environments properties
const {
  appVersion,
  api: {
    protocol,
    host,
    port
  }
} = environment;

// Configuration
export const APP_DI_CONFIG: AppConfig = Object.freeze({

  appVersion: `${appVersion}`,
  // APIs suffix based on environment variables
  api: {
    host: (`${protocol ? `${protocol}` : ''}${host ? `://${host}` : ''}${port ? `:${port}` : ''}`).trim(),
    suffix: Object.entries(environment.api).reduce((accumulator, [key, value]) => (
      value && !['protocol', 'host', 'port'].includes(key)
        ? `${accumulator}${accumulator && '/'}${(<string>value).trim()}`
        : accumulator
    ), '')
  },

  // Current app hostname
  location: {
    protocol: window.location.protocol,
    hostname: window.location.hostname,
    port: window.location.port
  }
});
