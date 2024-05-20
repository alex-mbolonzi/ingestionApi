import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { IngestionRequestDetailsComponent } from './ingestion-request-details.component';
import { IngestionRequestDetailsDetailComponent } from './ingestion-request-details-detail.component';
import { IngestionRequestDetailsUpdateComponent } from './ingestion-request-details-update.component';
import {
  IngestionRequestDetailsDeletePopupComponent,
  IngestionRequestDetailsDeleteDialogComponent,
} from './ingestion-request-details-delete-dialog.component';
import { ingestionRequestDetailsRoute, ingestionRequestDetailsPopupRoute } from './ingestion-request-details.route';

const ENTITY_STATES = [...ingestionRequestDetailsRoute, ...ingestionRequestDetailsPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IngestionRequestDetailsComponent,
    IngestionRequestDetailsDetailComponent,
    IngestionRequestDetailsUpdateComponent,
    IngestionRequestDetailsDeleteDialogComponent,
    IngestionRequestDetailsDeletePopupComponent,
  ],
  entryComponents: [IngestionRequestDetailsDeleteDialogComponent],
})
export class IngestionApiIngestionRequestDetailsModule {}
