import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetTherapyComponent } from './dataset-therapy.component';
import { DatasetTherapyDetailComponent } from './dataset-therapy-detail.component';
import { DatasetTherapyUpdateComponent } from './dataset-therapy-update.component';
import { DatasetTherapyDeletePopupComponent, DatasetTherapyDeleteDialogComponent } from './dataset-therapy-delete-dialog.component';
import { datasetTherapyRoute, datasetTherapyPopupRoute } from './dataset-therapy.route';

const ENTITY_STATES = [...datasetTherapyRoute, ...datasetTherapyPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetTherapyComponent,
    DatasetTherapyDetailComponent,
    DatasetTherapyUpdateComponent,
    DatasetTherapyDeleteDialogComponent,
    DatasetTherapyDeletePopupComponent,
  ],
  entryComponents: [DatasetTherapyDeleteDialogComponent],
})
export class IngestionApiDatasetTherapyModule {}
