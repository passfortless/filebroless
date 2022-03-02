import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FilesTableComponent } from './files-table.component';

@NgModule({
  declarations: [
    FilesTableComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    FilesTableComponent
  ]
})
export class FilesTableModule { }
