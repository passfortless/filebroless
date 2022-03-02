/*
 * Date: 4/3/20, 9:10 AM
 * Modified: 4/2/20, 12:41 PM
 * File name: app.interface.ts
 * Copyright: Aldermore Bank PLC (c) 2020
 */

// Configuration interface
export interface AppConfig {
  appVersion: string;
  api: {
    host: string;
    suffix: string;
  };
  location: {
    protocol: string;
    hostname: string;
    port: string;
  };
}
