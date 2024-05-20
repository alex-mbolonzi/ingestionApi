import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetDetailsComponent } from './dataset-details.component';
import { DatasetDetailsDetailComponent } from './dataset-details-detail.component';
import { DatasetDetailsUpdateComponent } from './dataset-details-update.component';
import { DatasetDetailsDeletePopupComponent, DatasetDetailsDeleteDialogComponent } from './dataset-details-delete-dialog.component';
import { datasetDetailsRoute, datasetDetailsPopupRoute } from './dataset-details.route';

const ENTITY_STATES = [...datasetDetailsRoute, ...datasetDetailsPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetDetailsComponent,
    DatasetDetailsDetailComponent,
    DatasetDetailsUpdateComponent,
    DatasetDetailsDeleteDialogComponent,
    DatasetDetailsDeletePopupComponent,
  ],
  entryComponents: [DatasetDetailsDeleteDialogComponent],
})
export class IngestionApiDatasetDetailsModule {}
