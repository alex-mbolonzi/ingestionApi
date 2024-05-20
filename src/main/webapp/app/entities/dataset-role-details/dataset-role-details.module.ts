import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetRoleDetailsComponent } from './dataset-role-details.component';
import { DatasetRoleDetailsDetailComponent } from './dataset-role-details-detail.component';
import { DatasetRoleDetailsUpdateComponent } from './dataset-role-details-update.component';
import {
  DatasetRoleDetailsDeletePopupComponent,
  DatasetRoleDetailsDeleteDialogComponent,
} from './dataset-role-details-delete-dialog.component';
import { datasetRoleDetailsRoute, datasetRoleDetailsPopupRoute } from './dataset-role-details.route';

const ENTITY_STATES = [...datasetRoleDetailsRoute, ...datasetRoleDetailsPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetRoleDetailsComponent,
    DatasetRoleDetailsDetailComponent,
    DatasetRoleDetailsUpdateComponent,
    DatasetRoleDetailsDeleteDialogComponent,
    DatasetRoleDetailsDeletePopupComponent,
  ],
  entryComponents: [DatasetRoleDetailsDeleteDialogComponent],
})
export class IngestionApiDatasetRoleDetailsModule {}
