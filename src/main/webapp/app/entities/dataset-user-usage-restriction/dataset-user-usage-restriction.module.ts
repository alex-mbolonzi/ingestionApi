import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { DatasetUserUsageRestrictionComponent } from './dataset-user-usage-restriction.component';
import { DatasetUserUsageRestrictionDetailComponent } from './dataset-user-usage-restriction-detail.component';
import { DatasetUserUsageRestrictionUpdateComponent } from './dataset-user-usage-restriction-update.component';
import {
  DatasetUserUsageRestrictionDeletePopupComponent,
  DatasetUserUsageRestrictionDeleteDialogComponent,
} from './dataset-user-usage-restriction-delete-dialog.component';
import { datasetUserUsageRestrictionRoute, datasetUserUsageRestrictionPopupRoute } from './dataset-user-usage-restriction.route';

const ENTITY_STATES = [...datasetUserUsageRestrictionRoute, ...datasetUserUsageRestrictionPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DatasetUserUsageRestrictionComponent,
    DatasetUserUsageRestrictionDetailComponent,
    DatasetUserUsageRestrictionUpdateComponent,
    DatasetUserUsageRestrictionDeleteDialogComponent,
    DatasetUserUsageRestrictionDeletePopupComponent,
  ],
  entryComponents: [DatasetUserUsageRestrictionDeleteDialogComponent],
})
export class IngestionApiDatasetUserUsageRestrictionModule {}
