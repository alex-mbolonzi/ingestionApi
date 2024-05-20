import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { RequestStatusDetailsComponent } from './request-status-details.component';
import { RequestStatusDetailsDetailComponent } from './request-status-details-detail.component';
import { RequestStatusDetailsUpdateComponent } from './request-status-details-update.component';
import {
  RequestStatusDetailsDeletePopupComponent,
  RequestStatusDetailsDeleteDialogComponent,
} from './request-status-details-delete-dialog.component';
import { requestStatusDetailsRoute, requestStatusDetailsPopupRoute } from './request-status-details.route';

const ENTITY_STATES = [...requestStatusDetailsRoute, ...requestStatusDetailsPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RequestStatusDetailsComponent,
    RequestStatusDetailsDetailComponent,
    RequestStatusDetailsUpdateComponent,
    RequestStatusDetailsDeleteDialogComponent,
    RequestStatusDetailsDeletePopupComponent,
  ],
  entryComponents: [RequestStatusDetailsDeleteDialogComponent],
})
export class IngestionApiRequestStatusDetailsModule {}
