import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetIndicationComponent } from './dataset-indication.component';
import { DatasetIndicationDetailComponent } from './dataset-indication-detail.component';
import { DatasetIndicationUpdateComponent } from './dataset-indication-update.component';
import {
  DatasetIndicationDeletePopupComponent,
  DatasetIndicationDeleteDialogComponent,
} from './dataset-indication-delete-dialog.component';
import { datasetIndicationRoute, datasetIndicationPopupRoute } from './dataset-indication.route';

const ENTITY_STATES = [...datasetIndicationRoute, ...datasetIndicationPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetIndicationComponent,
    DatasetIndicationDetailComponent,
    DatasetIndicationUpdateComponent,
    DatasetIndicationDeleteDialogComponent,
    DatasetIndicationDeletePopupComponent,
  ],
  entryComponents: [DatasetIndicationDeleteDialogComponent],
})
export class IngestionApiDatasetIndicationModule {}
