export interface FileMetadata {
  name: string;
  directory: boolean;
  read: boolean;
  write: boolean;
  execute: boolean;
  length: number;
  lastModified: string;
}