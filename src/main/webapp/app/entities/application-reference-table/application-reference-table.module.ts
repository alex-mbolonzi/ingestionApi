import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { ApplicationReferenceTableComponent } from './application-reference-table.component';
import { ApplicationReferenceTableDetailComponent } from './application-reference-table-detail.component';
import { ApplicationReferenceTableUpdateComponent } from './application-reference-table-update.component';
import {
  ApplicationReferenceTableDeletePopupComponent,
  ApplicationReferenceTableDeleteDialogComponent,
} from './application-reference-table-delete-dialog.component';
import { applicationReferenceTableRoute, applicationReferenceTablePopupRoute } from './application-reference-table.route';

const ENTITY_STATES = [...applicationReferenceTableRoute, ...applicationReferenceTablePopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApplicationReferenceTableComponent,
    ApplicationReferenceTableDetailComponent,
    ApplicationReferenceTableUpdateComponent,
    ApplicationReferenceTableDeleteDialogComponent,
    ApplicationReferenceTableDeletePopupComponent,
  ],
  entryComponents: [ApplicationReferenceTableDeleteDialogComponent],
})
export class IngestionApiApplicationReferenceTableModule {}
